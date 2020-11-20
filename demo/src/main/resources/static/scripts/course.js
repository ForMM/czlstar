layui.use('table', function(){
    var table = layui.table;

    //第一个实例
    table.render({
        elem: '#demo'
        ,height: 500
        ,url: 'courseList' //数据接口
        ,page: true
        ,cols: [[ //表头
            {field: 'id', title: 'ID', width:80, sort: true, fixed: 'left'}
            ,{field: 'title', title: '课程主题', width:300}
            ,{field: 'subTitle', title: '课程子主题', width:300}
            ,{field: 'type', title: '类型', width:100}
            ,{field: 'price', title: '价格', width: 100, sort: true}
            ,{fixed: 'right', title: '操作',width:200, align:'center', toolbar: '#barDemo'} //这里的toolbar值是模板元素的选择器
        ]]
        ,request: {
            pageName: 'page' //页码的参数名称，默认：page
            ,limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
        ,method: 'post'
        ,response: {
            statusCode: 1 //重新规定成功的状态码为 200，table 组件默认为 0
        }
        ,parseData: function(res){ //res 即为原始返回的数据
            console.log(res);
            console.log(res.data.pageCount);
            console.log(res.data.dataList);
            return {
                "code": res.code, //解析接口状态
                "msg": res.msg, //解析提示文本
                "count": res.data.pageCount*res.data.pageSize, //解析数据长度
                "data": res.data.dataList //解析数据列表
            };
        }
    });



    //监听工具条
    table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）

        if(layEvent === 'detail'){ //查看

            console.log(data.id);

            var courseDetail = layer.open({
                type: 2,
                area: ['320px', '195px'],
                maxmin: true,
                content: 'addCourseDetailPage?courseId='+data.id
            });
            layer.full(courseDetail);



        } else if(layEvent === 'del'){ //删除
            layer.confirm('真的删除行么', function(index){
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                layer.close(index);
                //向服务端发送删除指令
            });
        } else if(layEvent === 'edit'){ //编辑
            //do something

            //同步更新缓存对应的值
            // obj.update({
            //     username: '123'
            //     ,title: 'xxx'
            // });
        } else if(layEvent === 'LAYTABLE_TIPS'){
            layer.alert('Hi，头部工具栏扩展的右侧图标。');
        }
    });



});

$(function(){
    $("#addBtn").click(function(){
        var index = layer.open({
            type: 2,
            area: ['320px', '195px'],
            maxmin: true,
            content: 'addCoursePage'
        });
        layer.full(index);
    });
});

