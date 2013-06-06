module = angular.module('myFeed.admin.controllers', ['ui.bootstrap'])

module.controller 'HomeController', ($scope, $state) ->

module.controller 'ListController', ($scope, $state, $dialog, Entry) ->
  $scope.field = "name"
  $scope.value = ""
  $scope.sort = "_id"
  $scope.order = 1
  $scope.page = 1
  $scope.limit = 20

  $scope.add = (id) ->
    opts =
      backdrop: true
      keyboard: true
      dialogFade: true
      backdropClick: true
      templateUrl: 'admin/feed/partials/detail'
      controller: 'DetailController'
      id: id
      reload: $scope.reload
    d = $dialog.dialog(opts)
    d.open()

  $scope.delete = (_id) ->
    title = 'Delete message'
    msg = "Do you want to delete entry id #{ _id }"
    btns = [{result:'cancel', label: 'Cancel'}, {result:'ok', label: 'OK', cssClass: 'btn-primary'}]
    $dialog.messageBox(title, msg, btns).open().then (result) ->
      if result is 'ok'
        Entry.delete {id: _id}
        $scope.reload()

  query = (field, value, sort, order, page, limit) ->
    Entry.query {f: field, v: value, s: sort, o: order, p: page, l: limit}, (dataTabe) ->
      $scope.field = dataTabe.field
      $scope.value = dataTabe.value
      $scope.sort = dataTabe.sort
      $scope.order = dataTabe.order
      $scope.page = dataTabe.page
      $scope.limit = dataTabe.limit
      $scope.data = dataTabe.data
      $scope.totalPage = dataTabe.totalPage

  reload = () ->
    query($scope.field, $scope.value, $scope.sort, $scope.order, $scope.page, $scope.limit)

  $scope.query = query
  $scope.reload = reload

  reload()

module.controller 'DetailController', ($scope, dialog, Entry) ->
  if dialog.options.id
    $scope.entry = Entry.query {id: dialog.options.id}

  $scope.save = ->
    error = (result) ->
      $scope.errors = result.data
    success = ->
      #reload data in table
      dialog.options.reload()
      dialog.close()

    if dialog.options.id
      Entry.update {id: $scope.entry._id}, $scope.entry, success, error
    else
      Entry.create $scope.entry, success, error

  $scope.close = ->
    dialog.close()

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