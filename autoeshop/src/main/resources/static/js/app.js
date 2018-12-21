//Application module
var app = angular.module('myApp', ['ngRoute', 'ngResource','ngCookies']);

app.config(function($routeProvider, $locationProvider){
	
	
	$routeProvider
	.when('/home', {
		templateUrl: 'home.html',
		controller: 'homeCtrl'
	})
	
	.when('/searchbrand', {
		templateUrl: 'searchbrand.html',
		controller: 'searchCtrl'
	})

	.when('/brand', {
		templateUrl: 'brand.html',
		controller: 'brandCtrl'
	})

	.when('/item', {
		templateUrl: 'item.html',
		controller: 'itemCtrl'
	})
	
	
	.when('/about', {
		templateUrl: 'about.html',
		controller: 'aboutCtrl'
	})
	
	.when('/signup', {
		templateUrl: 'signup.html',
		controller: 'signupCtrl'
	})
	
	.when('/login', {
		templateUrl: 'login.html',
		controller: 'loginCtrl'
	})
	
	.otherwise({
		redirectTo: '/home',
		controller: 'homeCtrl'
	});
});

app.controller('mainCtrl', function($scope, $location) {
	$scope.message = "k";
	$scope.outside =true;
	outside($scope.outside);

	$scope.isActive = function (viewLocation) { 
        return viewLocation === $location.path();
    };
})

function outside(params) {
	params = true;
}

app.controller('homeCtrl', function($scope) {
	$scope.message = "Auto E-Shop will be to help your car which has some an issue.";
})

app.controller('searchCtrl', function($scope, $http) {
	
	$scope.getAll = function() {
		$http.get('/autoeshop/items').then(function(response){
			$scope.items = response.data;
		});
	}

	$scope.submitButton = function(id) {
		console.log(id);
			$scope.submitted = true;
			
	}

	$scope.getBrandByItem = function(id) {
		$http.get('/autoeshop/item/' + id + '/brands').then(function successCallback(response){
			$scope.brands = response.data.content;
		}, function errorCallback(response){
			console.log("Unable to perform get request");
		});
	}

	
})

app.controller('itemCtrl', function($scope, $http) {
	
	$scope.getAll = function() {
		$http.get('/autoeshop/items').then(function(response){
			$scope.items = response.data;
		});
	}

	//open modal form
	$scope.openModal = function() {
		$(".updateButton").hide();
		$(".createButton").show();
		$('#myModalLabel').text("Add item");
		$scope.clearForm();
		$('#myModalForm').modal();
	}

	//clear form
	$scope.clearForm = function() {
		
		$scope.data = {
			name: "",
			provider: "",
			quantity: "",
			price: ""
		};
	}

	$scope.create = function() {		

		$http.post('/autoeshop/item', $scope.data).then(function(response, data) {

			//close modal
			$('#myModalForm').modal('hide');
			$scope.clearForm();

			//refresh the list
			$scope.getAll();
		});
	}

	$scope.edit = function(id) {

		$http.get('/autoeshop/item/' + id).then(function(response) {
			
			$scope.data = {
				id: response['data']['id'],
				name: response['data']['name'],
				provider: response['data']['provider'],
				quantity: response['data']['quantity'],
				price: response['data']['price']
			}

			$(".createButton").hide();
			$(".updateButton").show();
			$('#myModalLabel').text("Edit item");
			$('#myModalForm').modal();
		});
	}

	$scope.update = function() {
		
		$http.put('/autoeshop/item/' + $scope.data.id, $scope.data).then(function(response) {
			$('#myModalForm').modal('hide');  
		    $scope.getAll();
		});
	}

	$scope.delete = function(id) {

		if (confirm("Are you sure you want to delete !!!")) {
			$http.delete('/autoeshop/item/' + id).then(function(reponse) {
				$scope.getAll();
			});
		}
	}
})

app.controller('brandCtrl', function($scope, $http) {

	$scope.getAll = function() {
		$http.get('/autoeshop/items').then(function(response){
			$scope.items = response.data;
			$scope.items.brands = $scope.brands;
		});
	}

	$scope.getBrandByItem = function(id) {
		console.log(id);
		$http.get('/autoeshop/item/' + id + '/brands').then(function successCallback(response){
			$scope.brands = response.data.content;
		}, function errorCallback(response){
			console.log("Unable to perform get request");
		});
	}

	//open modal form
	$scope.openModal = function() {
		$(".updateButton").hide();
		$(".createButton").show();
		$('#myModalLabel').text("Add auto-brand");
		$scope.clearForm();
		$('#myModalForm').modal();
		$scope.selectitem = $scope.items[0];
	}

	//clear form
	$scope.clearForm = function() {
		
		$scope.data = {
			name: "",
			model: "",
			price: ""
		};
	}
	
	$scope.createBrand = function() {
		
		
		$http.post('/autoeshop/item/' + $scope.selectitem.id + '/brand', $scope.data).then(function(response, data) {

			//close modal
			$('#myModalForm').modal('hide');
			$scope.clearForm();

			//refresh the list
			$scope.getAll();
		});
	}

	$scope.editBrand = function(id, brandid) {

		$http.get('/autoeshop/item/' + id + '/brand/' + brandid).then(function(response) {
			
			$scope.data = {
				id: response['data']['brandid'],
				name: response['data']['name'],
				model: response['data']['year'],
				year: response['data']['year'],
				itemid: id
			}

			$(".createButton").hide();
			$(".updateButton").show();
			$('#myModalLabel').text("Edit auto-brand");
			$('#myModalForm').modal();
		});
	}

	$scope.updateBrand = function() {
		
		$http.put('/autoeshop/item/' + $scope.data.itemid + '/brand/' + $scope.data.id,
		 $scope.data).then(function(response) {
			$('#myModalForm').modal('hide');  
		    $scope.getAll();
		});
	}

	$scope.deleteBrand = function(id, brandid) {

		if (confirm("Are you sure you want to delete !!!")) {
			$http.delete('/autoeshop/item/' + id + '/brand/' + brandid).then(function(reponse) {
				$scope.getAll();
			});
		}
	}

})

app.controller('loginCtrl', 
function($scope, $resource, $http, $httpParamSerializer, $cookies) {
   
  $scope.data = {
	  grant_type:"password", 
	  username: "", 
	  password: "", 
	  client_id: "clientid"
  };
  
  $scope.encoded = btoa("clientid:pwd");
   
  $scope.login = function() {   
	  var req = {
		  method: 'POST',
		  url: "http://localhost:8081/oauth/token",
		  headers: {
			  "Authorization": "Basic " + $scope.encoded,
			  "Content-type": "application/x-www-form-urlencoded; charset=utf-8"
		  },
		  data: $httpParamSerializer($scope.data)
	  }
	  $http(req).then(function(data){
		  $http.defaults.headers.common.Authorization = 
			'Bearer ' + data.data.access_token;
		  $cookies.put("access_token", data.data.access_token);
		  
		  window.location.href="/";
	  });   
 }    
});

app.controller('aboutCtrl', function($scope) {
	$scope.message = "2018";
})

app.controller('signupCtrl', function($scope) {
	$scope.inside = false;
	
})