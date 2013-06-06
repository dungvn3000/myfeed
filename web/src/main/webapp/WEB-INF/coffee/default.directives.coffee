module = angular.module('myFeed.directives', [])


#Prevent mousewheel scrolling from propagating to the parent when scrollbar reaches top or bottom
#author: CommaFeed
module.directive 'mousewheelScrolling', () ->
  restrict: 'A'
  link: (scope, elem, attr) ->
    elem.bind 'mousewheel', (e, d) ->
      t = $(this)
      if d > 0 and t.scrollTop() is 0
        e.preventDefault()
      else
        if d < 0 and t.scrollTop() is t.get(0).scrollHeight - t.innerHeight()
          e.preventDefault()

#Fired when the top of the element is not visible anymore
#author: CommaFeed
module.directive 'onScrollMiddle', () ->
  restrict: 'A'
  link: (scope, element, attrs) ->
    w = $(window)
    e = $(element)
    d = $(document)

    down = ->
      docTop = w.scrollTop()
      elemTop = e.offset().top
      threshold = if docTop is 0 then elemTop - 1 else docTop + w.height() / 3
      if (elemTop > threshold) then 'below' else 'above'

#    up = ->
#      docTop = w.scrollTop()
#      elemTop = e.offset().top
#      elemBottom = elemTop + e.height()
#      threshold = if docTop is 0 then elemBottom - 1 else docTop + w.height() / 3
#      if (elemBottom > threshold) then 'below' else 'above'

    w.data.scrollPosition = d.scrollTop()
    w.data.scrollDirection = 'down'

    if !w.data.scrollInit
      w.bind 'scroll', (e) ->
        scroll = d.scrollTop()
        w.data.scrollDirection = if scroll - w.data.scrollPosition > 0 then 'down' else 'up'
        w.data.scrollPosition = scroll
        scope.$apply()
      w.data.scrollInit = true

    scope.$watch down, (value, oldValue) ->
      if w.data.scrollDirection is 'down' and value isnt oldValue and value is 'above'
        scope.$eval(attrs.onScrollMiddle)

#    scope.$watch up, (value, oldValue) ->
#      if w.data.scrollDirection is 'up' and value isnt oldValue and value is 'below'
#        scope.$eval(attrs.onScrollMiddle)