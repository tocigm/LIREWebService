/**
 * Created by kidio on 2016. 10. 27..
 */
'use-strict';

angular
    .module('myApp')
    .factory('Item', function () {
        var self = this;

        self.init  = function(){
            return self = {
                name: '',
                url: '',
                score: ''
            };
        }

        self.set = function(object){
            return self = {
                name: object.name,
                url: object.url,
                score: object.score
            }
        }

        return self
    })