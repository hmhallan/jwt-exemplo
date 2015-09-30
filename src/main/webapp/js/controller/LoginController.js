(function(){
	
	angular.module('exemplo.controllers').controller('LoginController', ['$rootScope', '$scope', '$localStorage', '$location', 'UserAuthFactory', 'AuthenticationFactory', function($rootScope, $scope, $localStorage, $location, UserAuthFactory, AuthenticationFactory) {
        
        $scope.showMessage = false;
        
        $scope.user = {};

        //faz a requisicao de login
        $scope.login = function() {

            var username = $scope.user.username;
            var password = $scope.user.password;

            if (username !== undefined && password !== undefined) {
                
                UserAuthFactory.login(username, password).success(function(data, status, headers) {
                    $scope.showMessage = false;

                    //no sucesso, armazena os dados do usuario    
                    AuthenticationFactory.store(data, headers('Authorization'));
                    
                    //emite o evento de login
                    $rootScope.$broadcast('loginEvent', { message: 'Log in' });
                    
                    //altera o path para a raiz
                    $location.path("/");

                }).error(function(data, status) {
                    if (status == 401){
                        $scope.message = "Usuário/senha inválidos";
                    }
                    else{
                        $scope.message = "Ocorreu um erro.";
                    }
                     $scope.showMessage = true;
                });
                } else {
                    $scope.showMessage = true;
                    $scope.message = "Preencha os dados abaixo";
                }
        };

      }
    ]);
	
}).call(this);