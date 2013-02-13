Ext.onReady(function(){
	var example1 = new Ext.Panel({
		applyTo:'ch15_1',
		title:'15장 예제 1',
		width:250,
		height:250,
		frame:true,
		autoLoad:{
			url:'example1ajax.html'
		}
	});
});