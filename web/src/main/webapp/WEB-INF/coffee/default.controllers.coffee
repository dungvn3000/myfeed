module = angular.module('myFeed.controllers', [])

module.controller 'HomeController', ($scope, $state, $dialog, Feed) ->
  $scope.feeds = Feed.all (data) ->
    #Set current feed in case user reload feed detail page.
    if $state.params.feedId
      for feed in data
        $scope.currentFeed = feed if feed.id is $state.params.feedId

  $scope.getIconUrl = (feed) -> "https://plus.google.com/_/favicon?domain=#{ feed.url }&alt=feed" if feed?

  $scope.onFeedClick = (feed) -> $scope.currentFeed = feed

  $scope.openFeedAddDialog = ->
    opts =
      backdrop: true
      keyboard: true
      dialogFade: true
      backdropClick: true
      templateUrl: '/feed/partials/add'
      controller: 'FeedDialogController'
    d = $dialog.dialog(opts)
    d.open()

module.controller 'FeedController', ($scope, $state, Feed) ->
  homeController = $scope.$parent

  # define an empty array, we will push data in it later on.
  $scope.news = []
  # current page using for pagination
  $scope.page = 1
  $scope.loadMore = () ->
    callback = (data)->
      if data.length > 0
        $scope.busy = false
        $scope.page += 1
        $scope.news.push newItem for newItem in data
    $scope.busy = true
    Feed.getNews {feedId: $state.params.feedId, page: $scope.page, recommend: $state.params.recommend}, callback

  $scope.mark = (entry, clicked = false) ->
    if clicked
      Feed.mark {newsId: entry.id, clicked: clicked}

    if homeController.currentFeed and !entry.read
      if !entry.recommend
        homeController.currentFeed.recommend -= 1
      homeController.currentFeed.unread -= 1
      entry.read = true
      Feed.mark {newsId: entry.id, clicked: clicked}

module.controller 'FeedDialogController', ($scope, dialog, Feed) ->
  $scope.save = ->
    error = (result) -> $scope.errors = result.data
    success = -> location.reload()
    Feed.add $scope.feed, success, error

  $scope.close = -> dialog.close()

  $scope.getMsg = (key) ->
    msg = ""
    if $scope.errors
      for error in $scope.errors
        if error.key is key
          msg += error.msg + ", "
    if msg.length > 0 then msg.substring(0, msg.length - 2) else msg

  $scope.getClass = (key) ->
    style = ""
    if $scope.errors
      for error in $scope.errors
        if error.key is key
          style = "error"
    style