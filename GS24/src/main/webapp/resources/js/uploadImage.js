/**
 * 식품 사진 및 리뷰 사진 업로드 하는 코드
 */

 $(document).ready(function(){
 	// 파일 객체를 배열로 전달받아 검증하는 함수
		function validateImages(files){
			var maxSize = 10 * 1024 * 1024; // 10 MB 
			var allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i; 
			// 허용된 확장자 정규식 (jpg, jpeg, png, gif)
			
			console.log("files length : " +files.length);
			if(files.length > 3) { // 파일 개수 제한
				alert("파일은 최대 3개만 가능합니다.");
				return false;
			}
			
			for(var i = 0; i < files.length; i++) {
				console.log(files[i]);
				var fileName = files[i].name; // 파일 이름
				var fileSize = files[i].size; // 파일 크기
				
				// 파일 크기가 설정 크기보다 크면
				if (fileSize > maxSize) {
					alert("파일의 최대 크기는 10MB입니다.");
					return false;
				}
				
				// regExp.exec(string) : 지정된 문자열에서 정규식을 사용하여 일치 항목 확인
				// 지정된 문자열이 없는 경우 true를 리턴
		        if(!allowedExtensions.exec(fileName)) {
		            alert("이 파일은 업로드할 수 없습니다. jpg, jpeg, png, gif파일만 가능합니다."); // 알림 표시
		            return false;
		        }
			}

			return true; // 모든 조건을 만족하면 true 리턴
		} // end validateImage()

		
		// 파일을 끌어다 놓을 때(drag&drop)
		// 브라우저가 파일을 자동으로 열어주는 기능을 막음
		$('.image-drop').on('dragenter dragover', function(event){
			event.preventDefault();
			console.log('drag 테스트');
		}); 
		
		$('.image-drop').on('drop', function(event){
			event.preventDefault();
			console.log('drop 테스트');
			console.log($(".image-item").length);
							
			// 드래그한 파일 정보를 갖고 있는 객체
			var files = event.originalEvent.dataTransfer.files;
			console.log(files);
			
			if(!validateImages(files)) { 
				return;
			}
			
			if($(".image-item").length > 3){
				$('.image-list').empty(); // 기존 이미지 dto 초기화
			}
			
			if((files.length + $(".image-item").length) > 3){
				alert("출력할 수 있는 이미지는 최대 3개 입니다.");
				return;
			}
			
			// Ajax를 이용하여 서버로 파일을 업로드
			// multipart/form-data 타입으로 파일을 업로드하는 객체
			var formData = new FormData();
			for(var i = 0; i < files.length; i++) {
				formData.append("files", files[i]); 
			}
			formData.append("foreignId",$(".foreignId").val());
			formData.append("type",$(".type").val());
			$.ajax({
				type : 'post', 
				url : '../image/', 
				data : formData,
				processData : false,
				contentType : false,
				success : function(data) {
					var list =""
						$(data).each(function(){
							console.log(this);
							var ImgVO = this;
							var ImgPath = encodeURIComponent(ImgVO.imgPath);
							console.log(ImgPath);
							var input = $('<input>').attr('type', 'hidden').attr('name', 'ImgVO').attr('data-chgName', ImgVO.imgChgName);
							
							input.val(JSON.stringify(ImgVO));
							
							$('.ImgVOList').append(input);
							
							list += '<div class="image-item" data-chgName="'+ this.imgChgName +'"'
								 +	'<pre>'
								 +	'<input type="hidden" id="ImgPath" value="'+ ImgVO.imgPath +'">'
								 +	'<input type="hidden" id="ImgChgName" value="'+ ImgVO.imgChgName +'">'
								 +	'<input type="hidden" id="ImgExtension" value="'+ ImgVO.imgExtension +'">'
								 +	'<img src="../image/display?path='+ ImgPath + "&chgName=" + ImgVO.imgChgName +"&extension=" + ImgVO.imgExtension + '" target="_blank" width="100px" height="100px" />'
								 +	'</pre>'
								 +	'</div>';
						});// end each 
					
					var htmlList = $(".image-list").html();
					htmlList = htmlList + list;
					$(".image-list").html(htmlList);
				} // end success
			}); // end $.ajax()
			
		}); // end image-drop()
		
		$(".cancel").click(function(){			
			var path = $(".image-item").find("input[id=ImgPath]").val();
			console.log(path);
			var buttonValue = $(this).val();
			console.log(buttonValue);
			$.ajax({
				type : 'post',
				url : '../image/remove',
				data : {'path': path},
				success: function(result){
					$(".image-list").empty();
					$(".ImgVOList").remove();
				}
			});//end ajax
			
			if(buttonValue == 'cancel'){
				location.href="../food/list";
			}
		});// end click
 });