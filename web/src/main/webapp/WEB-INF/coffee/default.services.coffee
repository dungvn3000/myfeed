module = angular.module('myFeed.services', ['ngResource'])

module.factory "Feed", ($resource) ->
  actions =
    all:
      method: 'GET'
      isArray: true

    getNews:
      method: 'GET'
      params:
        method: 'getNews'
      isArray: true

    mark:
      method: 'POST'
      params:
        method: 'mark'

    add:
      method: 'POST'
      params:
        method: 'add'
      isArray: true

  $resource '/feed/:method/:feedId', {}, actions

