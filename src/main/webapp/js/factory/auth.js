(function(){
    
	angular.module('exemplo.auth', [])
    
        //checa o status no lado cliente
        .factory('AuthenticationFactory', ['$http', '$localStorage', function($http, $localStorage) {
          var auth = {
              
            isLogged: false,
              
            check: function() {
              if ($localStorage.token) {
                this.isLogged = true;
              } else { 
                this.clean();
              }
            },
              
            //dados do post para o login
            store: function (data, token){
                this.isLogged = true;
                $localStorage.token = token;
                $localStorage.user = data.sub;
            },
              
            clean: function(){
                this.isLogged = false;
                delete $localStorage.token;
                delete $localStorage.user;
            },
              
            user: function(){
                return $localStorage.user;
            },
            token: function(){
                return $localStorage.token;
            }
              
          }

          return auth;
        }])

        //faz o contato com o endpoint e tambem faz o logout
        .factory('UserAuthFactory', function($localStorage, $location, $http, AuthenticationFactory) {
          return {
            login: function(username, password) {
                return $http.post('./auth/login', { username: username, password: password } );
            },
            logout: function() {
              if (AuthenticationFactory.isLogged) {
                AuthenticationFactory.clean();
                $location.path("/login");
              }
            }
          }
        })

       
        //interceptor
        .factory('TokenInterceptor', ['$q', '$location', '$localStorage', function($q, $location, $localStorage) {
            return {
                'request': function (config) {
                    config.headers = config.headers || {};
                    if ($localStorage.token) {
                        config.headers['Authorization'] = $localStorage.token;
                    }
                    
                    return config;
                },
                'responseError': function(response) {
                    if(response.status === 401) {
                        $location.path('/login');
                    }
                    return $q.reject(response);
                }
            };
        }])
    
    ;
    
}).call(this);