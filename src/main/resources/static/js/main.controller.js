'use strict';

angular
    .module('myApp')
    .controller('MainCtr', ['$scope','Item','Upload', '$timeout', MainCtr])


function MainCtr($scope, Item, Upload, $timeout){


    $scope.items = []
    $scope.checkedIndex = 0
    $scope.total = 0.0
    $scope.laborCost = 0.0
    $scope.materialCost = 0.0

    $scope.picFile = "http://placehold.it/300x300"

    $scope.selectItem = function (index) {
        $scope.checkedIndex = index
        calculateTotal()
    }

    function calculateTotal() {
        $scope.materialCost = parseFloat($scope.items[$scope.checkedIndex].price.replace(",", ".")) * $scope.quantity
        $scope.laborCost = 30 + $scope.quantity * 5
        $scope.total =  parseFloat($scope.materialCost + $scope.laborCost).toFixed(2)
    }

    $scope.checkIndex = function (index) {
        return $scope.checkedIndex == index
    }

    $scope.itemClass = function (index) {
        if ($scope.checkedIndex == index)
            return "material-class"
        else ""
    }

    $scope.quantity = 0

    $scope.incQuantity = function () {
        $scope.quantity = $scope.quantity + 1
        calculateTotal()

    }

    $scope.decQuantity = function () {
        $scope.quantity = $scope.quantity  - 1
        calculateTotal()
    }


    $scope.uploadPic = function (file) {
        $scope.items = [];
        $scope.file = file
        file.upload = Upload.upload({
            url: 'http://localhost:1234/search',  //http://demo7177.cloudapp.net/upload
            data: {file: file}
        })
        file.upload.then(function (response) {
            console.log(response)
            $timeout(function () {
                response.data.forEach(function (item) {
                    $scope.items.push(Item.set(item))
                })

                // $scope.items = $scope.items.sort(function (a, b) {
                //     return a.score > b.score
                // }).slice(0,5)
            })
        })
    }

}
