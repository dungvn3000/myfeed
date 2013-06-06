module = angular.module('myFeed.filters', [])

module.filter('interpolate',
  ['version', (version)->
    (text)->
      text.replace(/\%VERSION\%/mg, version)
  ])