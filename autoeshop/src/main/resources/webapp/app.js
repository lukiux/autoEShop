var app = angular.module('myApp', ["ngResource", "ngRoute", "oauth"]);

app.config(function($locationProvider) {
	$locationProvider.html5Mode({
		enabled: true,
		requireBase: false
	}).hashPrefix('!');
});

