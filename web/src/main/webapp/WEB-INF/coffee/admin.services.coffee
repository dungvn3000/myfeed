module = angular.module('myFeed.admin.services', ['ngResource'])

module.factory "Entry", ($resource) ->
  actions =
    query:
      method: 'GET'

    update:
      method: 'PUT'
      isArray: true

    create:
      method: 'POST'
      isArray: true

    delete:
      method: 'DELETE'

  $resource '/admin/feed/:id', {}, actions