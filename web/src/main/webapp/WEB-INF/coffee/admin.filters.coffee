module = angular.module('myFeed.admin.filters', [])

module.filter 'range', () ->
  (input, total) ->
    for i in [1..total] by 1
      input.push i
    input
