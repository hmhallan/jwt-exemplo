
(function(){

    angular.module('jwt.controllers').controller('HomeController', ['$scope','AuthenticationFactory', '$http',
        function($scope, AuthenticationFactory, $http){

             $scope.dadosUsuarioLocal = AuthenticationFactory.user();
            
            
            
             $http.get("./rest/usuario/dados").then(function(response){
                 $scope.dadosUsuarioRemoto = response.data;
             });
            
             $http.get("./rest/usuario/protegido")
                 .then(function(response){
                     $scope.dadosProtegido = response.data;
                     $scope.statusProtegido = response.status;
                 }, function(response){
                     $scope.dadosProtegido = response.data;
                     $scope.statusProtegido = response.status;
                 });


        }]);

}).call(this);