    var app = angular.module("EMPLOYEE_LIST", []);
    app.controller("EMPLOYEE_LIST_CTRL", function($scope, $http){
        var documentUrl = new URL (document.URL);
        $scope.pageAttr = new URL (document.URL).searchParams.get("page");
        $scope.sortDirection = documentUrl.searchParams.get("dir");
        console.log($scope.pageAttr);
        $scope.pageSize = documentUrl.searchParams.get("size");
        $scope.pageArray = [];
        $scope.previousPage = 0;
        $scope.nextPage= Number(pageAttr) + 1;
        $scope.contentUrl =  "/employee";
        
        $scope.contentlist = [];
        $scope.getContetntlist = function(contentUrl){
        $http({
            url: contentUrl + "/pages?page=" + $scope.pageAttr + "&dir=" + $scope.sortDirection+"&size=" + $scope.pageSize,
            method: "GET"
        }).then(function(response){
            $scope.contentlist = response.data.content;
           
            var totalPages = response.data.totalPages;
            $scope.createPaginationParams(totalPages);
            console.log(response.data);
            console.log($scope.pageArray);
        });
        }


        $scope.createPaginationParams = function(totalPages){
            if (pageAttr > 0){
                $scope.previousPage = pageAttr - 1;
            }
            if (pageAttr >= totalPages-1){
                $scope.nextPage = totalPages-1;
            }
            $scope.pageArray = [];
            for (let i =0; i < totalPages; i++){
                $scope.pageArray.push(i);        
            }        
        }



        $scope.deleteEmployee = function(delId){
                $http({
                url: "/employee/"+delId,
                method: "DELETE"
            }).then(function(response){
                $scope.employee = response.data;
                $scope.getEmployees();
            });
            }

        $scope.getContetntlist($scope.contentUrl);

    });


