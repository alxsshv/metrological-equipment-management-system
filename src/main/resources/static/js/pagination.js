    var app = angular.module("PAGINATION", []);
    app.controller("PAGINATION_CTRL", function($scope, $http){
        
        $scope.getPageAttributes();
        $scope.getPagesContentlist = function(contentUrl){
        $http({
            url: contentUrl + "/pages?page=" + $scope.pageNum + "&dir=" + $scope.sortDirection+"&size=" + $scope.pageSize,
            method: "GET"
        }).then(function(response){
            $scope.contentlist = response.data.content;
            $scope.createPaginationParams(response.data.totalPages);
        });
        }

        
        $scope.getPageAttributes = function(){
            var documentUrl = new URL (document.URL);
            $scope.pageNum = documentUrl.searchParams.get("page");
            $scope.sortDirection = documentUrl.searchParams.get("dir");
            $scope.pageSize = documentUrl.searchParams.get("size");
        
            if ($scope.pageSize == null || $scope.pageSize == undefined){
                $scope.pageSize = "10";
            }
            if ($scope.sortDirection == null || $scope.sortDirection == undefined){
                $scope.sortDirection = "ASC";
            }
            if ($scope.pageNum == null || $scope.pageNum == undefined){
                $scope.pageNum = "0";
            }
        }
        
        $scope.createPaginationParams = function(totalPages){
            $scope.pageArray = [];
            $scope.previousPage = 0;
            $scope.nextPage= Number($scope.pageNum) + 1;

            if ($scope.pageNum > 0){
                $scope.previousPage = $scope.pageNum - 1;
            }
            if ($scope.pageNum >= totalPages-1){
                $scope.nextPage = totalPages-1;
            }
            $scope.pageArray = [];
            for (let i =0; i < totalPages; i++){
                $scope.pageArray.push(i);        
            }        
        }


    });


