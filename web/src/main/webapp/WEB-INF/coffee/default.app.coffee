app = angular.module('myFeed', ['myFeed.filters', 'myFeed.services', 'myFeed.directives', 'myFeed.controllers',
                                'ui.utils', 'infinite-scroll', 'ui.bootstrap','ui.compat'])

app.config ($routeProvider, $stateProvider, $urlRouterProvider) ->
  $stateProvider.state 'feeds', {
    'abstract': true
    url: '/feed'
    templateUrl: '/feed/partials/feeds'
  }

  $stateProvider.state 'feeds.list', {
    url: ''
    templateUrl: '/feed/partials/list'
    controller: 'FeedController'
  }

  $stateProvider.state 'feeds.detail', {
    url: '/:feedId&:recommend'
    templateUrl: '/feed/partials/detail'
    controller: 'FeedController'
  }

  $urlRouterProvider.otherwise("/feed")

app.run ($rootScope, $state, $stateParams) ->
  $rootScope.$state = $state
  $rootScope.$stateParams = $stateParams