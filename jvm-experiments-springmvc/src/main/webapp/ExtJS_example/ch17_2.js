Ext.onReady(function(){
	var formPanel = new Ext.form.FormPanel({
		title:'시간 필드 예제',
		applyTo:'ch17_2',
		layout:'form',
		labelAlign:'top',
		width:210,
		autoHeight:true,
		frame:true,
		items:[{
			xtype:'timefield',
			fieldLabel:'시간',
			minValue: '9:00 AM',
			maxValue: '6:00 PM',
			increment: 30
		}]
	});
});