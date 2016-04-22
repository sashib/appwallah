"""Ideas API implemented using Google Cloud Endpoints."""

import endpoints
from google.appengine.ext import ndb
from protorpc import remote

from endpoints_proto_datastore.ndb import EndpointsModel

package = 'com.appwallah.ideas'

WEB_CLIENT_ID = '654578331704-s040ut4to787puikukkj18h4oahb28gp.apps.googleusercontent.com'
ANDROID_CLIENT_ID = '654578331704-86itfio8tb5odk3isoehfhj27k8dke70.apps.googleusercontent.com'
ANDROID_AUDIENCE = WEB_CLIENT_ID

class Idea(EndpointsModel):
  description = ndb.StringProperty(required=True)
  created = ndb.DateTimeProperty(auto_now_add=True)
  date = ndb.DateProperty(auto_now_add=True)
  owner = ndb.UserProperty()
  hashtags = ndb.StringProperty(repeated=True)


@endpoints.api(name='ideasapi',
               allowed_client_ids=[ANDROID_CLIENT_ID,
                                   endpoints.API_EXPLORER_CLIENT_ID],
               audiences=[ANDROID_AUDIENCE],
               version='v1',
               description="The Ideas API")
class IdeasApi(remote.Service):

  @Idea.method(user_required=True,
               path='idea', http_method='POST', name='idea.insert')
  def IdeaInsert(self, idea):
    idea.owner = endpoints.get_current_user()
    idea.put()
    return idea

  @Idea.query_method(query_fields=('hashtags', 'date', 'order', 'limit', 'pageToken'),
                     collection_fields=('description', 'date', 'hashtags'),
                     user_required=True,
                     path='ideas', name='idea.list')
  def IdeaList(self, query):
    return query.filter(Idea.owner == endpoints.get_current_user())

APPLICATION = endpoints.api_server([IdeasApi])
