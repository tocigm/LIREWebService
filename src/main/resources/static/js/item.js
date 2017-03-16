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
                category: '',
                currency: '',
                inserted_at: '',
                item_id: '',
                price: '',
                image_url: '',
                score: ''
            };
        }

        self.set = function(object){
            return self = {
                name: object.name[0],
                category: object.category[0],
                currency: object.currency[0],
                inserted_at: object.inserted_at[0],
                item_id: object.item_id[0],
                price: object.price[0],
                image_url: object.image_url,
                score: object.score
            }
        }

        return self
    })