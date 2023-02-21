<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>상품 등록</title>
<%@ include file="../inc/header_link.jsp"%>
<style type="text/css">
	.box-style{
		width:90px;
		height:95px;
		border:1px solid #ccc;
		display:inline-block;
		margin:5px;
	}
	.box-style img{
		width:65px;
		height:55px;
	}
	.box-style div{
		text-align:right;
		margin-right:5px;
		font-weight:bold;
	}
</style>
</head>
<body class="hold-transition sidebar-mini layout-fixed">
	<div class="wrapper">

		<!-- Preloader -->
		<%@ include file="../inc/preloader.jsp" %>
		
		<!-- Navbar -->
		<%@ include file="../inc/navbar.jsp" %>
		<!-- /.navbar -->

		<!-- Main Sidebar Container -->
		<%@ include file="../inc/sidebar_left.jsp" %>
		
		
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<div class="content-header">
				<div class="container-fluid">
					<div class="row mb-2">
						<div class="col-sm-6">
							<h1 class="m-0">상품등록</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Home</a></li>
								<li class="breadcrumb-item active">상품관리> 상품등록</li>
							</ol>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content-header -->

			<!-- Main content -->
			<section class="content" id="app1">
				<div class="container-fluid">
				
					<!-- Main row -->
					<div class="row">
						<div class="col">

							<div class="form-group row">
								<select class="form-control" name="category_idx">
									<option>option 1</option>
									<option>option 2</option>
									<option>option 3</option>
									<option>option 4</option>
									<option>option 5</option>
								</select>
							</div>

							<div class="form-group row">
								<div class="col">
									<input type="text" name="product_name" class="form-control" placeholder="상품명">
								</div>
							</div>						
								
							<div class="form-group row">
								<div class="col">
									<input type="text" name="brand" class="form-control" placeholder="브랜드">
								</div>
							</div>		
												
							<div class="form-group row">
								<div class="col">
									<input type="number" name="price" class="form-control" placeholder="가격">
								</div>
							</div>			
											
							<div class="form-group row">
								<div class="col">
									<input type="number" name="discount" class="form-control" placeholder="할인">
								</div>
							</div>			
							
							<div class="form-group row">
								<div class="col">
									<input type="file" name="file" class="form-control" multiple>
								</div>
							</div>
							
							<div class="form-group row">
								<div class="col">
									<template v-for="json in imageList">
										<imagebox :src="json.src" :key="json.key" :idx="json.key"/>
									</template>
								</div>
							</div>
											
							<div class="form-group row">
								<div class="col">
									<textarea name="detail" class="form-control" id="detail"></textarea>
								</div>
							</div>


							<div class="form-group row">
								<div class="col-sm-1">
									<button type="button" class="btn btn-block btn-danger btn-lg">등록</button>							
								</div>
								<div class="col-sm-1">
									<button type="button" class="btn btn-block btn-danger btn-lg">목록</button>									
								</div>
							</div>							
							
						</div>
					</div>
					<!-- /.row (main row) -->
				</div>
				<!-- /.container-fluid -->
			
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		
		<%@ include file="../inc/footer.jsp" %>		

		<!-- Control Sidebar -->
		<%@ include file="../inc/sidebar_right.jsp" %>
		<!-- /.control-sidebar -->
	</div>
	<!-- ./wrapper -->
	<%@ include file="../inc/footer_link.jsp" %>
	<script type="text/javascript">
	let app1;
	let key=0;
	
	const imagebox={
		template:`
			<div class="box-style">
				<div @click=delImg(p_idx)><a href="#">X</a></div>
				<img :src="p_src"/>
			</div>	
		`,
		props:["src", "idx"],
		data(){
			return{
				p_src:this.src,
				p_idx:this.idx
			};
		},
		methods:{
			delImg:function(idx){
				for(let i=0;i<app1.imageList.length;i++){
					let json=app1.imageList[i];
					
					if(json.key == idx){
						app1.imageList.splice(i , 1); //요소,개수
					}
				}
			}
		}
	}
	
	
	function init(){
		app1=new Vue({
			el:"#app1",
			data:{
				count:3,
				imageList:[]
				
			},
			components:{
				imagebox
			}
		});
	}
	
	
	function preview(files){
		
		for(let i=0; i<files.length; i++){
			let file=files[i];
			
			let reader=new FileReader();
			reader.onload=function(e){
				console.log(file);
				key++;
				
				let json=[];
				json['src']=e.target.result;
				json['name']=file.name;
				json['file']=file;
				json['key']=key;
				
				app1.imageList.push(json);
			};
			
			reader.readAsDataURL(file);
			console.log("앱 1의 이미지 리스트~~~", app1.imageList);
		}
		
	}
	
	
	
	$(function(){
		// 써머 노트 적용
		$('#detail').summernote({
			height:200
		});
		
		// 뷰 적용
		init();
		
		$("input[name='file']").change(function(){
			preview(this.files);
		});
		
	});

	</script>
</body>
</html>

