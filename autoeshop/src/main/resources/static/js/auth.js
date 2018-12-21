angular.module('myApp').factory('AuthInterceptor', [function(){
	return {
		'request': function(config) {
			config.headers = config.headers ||{};
			var encodedString = btoa("clientid:pwd");
			config.headers.Authorization = 'Basic' + encodedString;
			return config;
		}
	};
}]);