// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
angular.module('starter', ['ionic', 'ui.router'])

.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    if(window.cordova && window.cordova.plugins.Keyboard) {
      // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
      // for form inputs)
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);

      // Don't remove this line unless you know what you are doing. It stops the viewport
      // from snapping when text inputs are focused. Ionic handles this internally for
      // a much nicer keyboard experience.
      cordova.plugins.Keyboard.disableScroll(true);
    }
    if(window.StatusBar) {
      StatusBar.styleDefault();
    }
  });
})


.config(function($stateProvider, $urlRouterProvider, $locationProvider) {
  $urlRouterProvider.otherwise('/')

  $stateProvider.state('first', {
    url: '/',
    templateUrl: 'templates/first.html'
  })
  
  .state('home', {
    url: '/home',
    templateUrl: 'templates/home.html',
      controller:'getconversion'
  })
})

 .controller('getconversion', function ($scope, $ionicModal, $location, $state, $http) {
    
    $scope.getdata = function () {
      $location.path('/home');
     $state.go('home');  
    };
             console.log('first');
    euros = document.getElementById('euros').value;
        $scope.euros1='value: '+euros;
    var conversion = { };
    console.log('start')
    $http.get('http://api.fixer.io/latest').then(function(data1) {
                       console.log(data1)
                       var temp=data1.data.rates.INR*euros;
        //temp=temp*data1.data.rates.INR
console.log(euros);    
        $scope.x=data1.data.rates.INR*euros;
        
        //$scope.val='Value is: '+data.data.main.value;
       // $scope.desc='Description: '+data.data.conversion[0].description;
                    //console.log(x);
                                       //         $scope.main=data.main;
        //console.log(main);
                                                //$scope.val = data.main.val;
                                            })
    
    
        
       })
    
    
