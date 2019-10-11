(function(){
	var oldData;
	var html = '';
	html += '<a class="diy export" data-type="json">导出json</a>',
	html += '<a class="diy export" data-type="md">导出md</a>',
	html += '<a class="diy export" data-type="km">导出km</a>',
	html += '<button class="diy input">',
	html += '导入<input type="file" id="fileInput">',
	html += '</button>';

	$('.editor-title').append(html);

	$('.diy').css({
		// 'height': '30px',
		// 'line-height': '30px',
		'margin-top': '0px',
		'float': 'right',
		'background-color': '#fff',
		'min-width': '60px',
		'text-decoration': 'none',
		color: '#999',
		'padding': '0 10px',
		border: 'none',
		'border-right': '1px solid #ccc',
	});
	$('.input').css({
		'overflow': 'hidden',
		'position': 'relative',
	}).find('input').css({
		cursor: 'pointer',
		position: 'absolute',
		top: 0,
		bottom: 0,
		left: 0,
		right: 0,
		display: 'inline-block',
		opacity: 0
	});
	$('.export').css('cursor','not-allowed');

	$(document).on('mouseover', '.export', function(event) {
		// 链接在hover的时候生成对应数据到链接中
		event.preventDefault();
		var $this = $(this),
				type = $this.data('type'),
				exportType;
		switch(type){
			case 'km':
				exportType = 'json';
				break;
			case 'md':
				exportType = 'markdown';
				break;
			default:
				exportType = type;
				break;
		}
		if(JSON.stringify(oldData) == JSON.stringify(editor.minder.exportJson())){
			return;
		}else{
			oldData = editor.minder.exportJson();
		}

		editor.minder.exportData(exportType).then(function(content){
			switch(exportType){
				case 'json':
					console.log($.parseJSON(content));
					break;
				default:
					console.log(content);
					break;
			}
			$this.css('cursor', 'pointer');
			var blob = new Blob([content]),
					url = URL.createObjectURL(blob);
			var aLink = $this[0];
			aLink.href = url;
			aLink.download = $('#node_text1').text()+'.'+type;
		});
	}).on('mouseout', '.export', function(event) {
		// 鼠标移开是设置禁止点击状态，下次鼠标移入时需重新计算需要生成的文件
		event.preventDefault();
		$(this).css('cursor', 'not-allowed');
	}).on('click', '.export', function(event) {
		// 禁止点击状态下取消跳转
		var $this = $(this);
		if($this.css('cursor') == 'not-allowed'){
			event.preventDefault();
		}
	});

	function init_data(){
		var json = {
            "root": {
                "data": {
                    "id": "btolevj7gd8g",
                    "created": 1556365185034,
                    "text": "java学习",
					"note": "java"
                },
                "children": [
                    {
                        "data": {
                            "id": "btolexhrc5c0",
                            "created": 1556365189300,
                            "text": "jdk",
                            "priority": 1,
                            "progress": 1,
                            "note": "jdk"
                        },
                        "children": []
                    },
                    {
                        "data": {
                            "id": "btolf96vzw1s",
                            "created": 1556365214764,
                            "text": "语法",
                            "priority": 2
                        },
                        "children": []
                    },
                    {
                        "data": {
                            "id": "btolfipg3t34",
                            "created": 1556365235478,
                            "text": "框架",
                            "priority": 1
                        },
                        "children": [
                            {
                                "data": {
                                    "id": "btolfw6rjy80",
                                    "created": 1556365264823,
                                    "text": "hibernate"
                                },
                                "children": []
                            },
                            {
                                "data": {
                                    "id": "btolg0g4z8jk",
                                    "created": 1556365274097,
                                    "text": "spring"
                                },
                                "children": []
                            }
                        ]
                    }
                ]
            },
            "template": "default",
            "theme": "fresh-blue",
            "version": "1.4.33"
        };
		var fileType = "json";
        var content = JSON.stringify(json);
        editor.minder.importData(fileType, content).then(function(data){
            console.log(data)
        });
	}

	// 导入
	window.onload = function() {

		init_data();

		var fileInput = document.getElementById('fileInput');

		fileInput.addEventListener('change', function(e) {
			var file = fileInput.files[0],
					// textType = /(md|km)/,
					fileType = file.name.substr(file.name.lastIndexOf('.')+1);
			console.log(file);
			switch(fileType){
				case 'md':
					fileType = 'markdown';
					break;
				case 'km':
				case 'json':
					fileType = 'json';
					break;
				default:
					console.log("File not supported!");
					alert('只支持.km、.md、.json文件');
					return;
			}
			var reader = new FileReader();
			reader.onload = function(e) {
				var content = reader.result;
				editor.minder.importData(fileType, content).then(function(data){
					console.log(data)
					$(fileInput).val('');
				});
			}
			reader.readAsText(file);
		});
	}

})();
