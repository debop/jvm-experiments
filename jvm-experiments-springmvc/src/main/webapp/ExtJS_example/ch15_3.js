Ext.onReady(function(){
	var ourStore = new Ext.data.Store({
		url:'ch15Example.cfc',
		baseParams:{
			method: 'getFileInfoByPath',
			returnFormat: 'JSON',
			queryFormat: 'column',
			startPath: '/images/'
		},
		reader: new Ext.data.CFQueryReader({
			id:'name'
		},[
			{name:'file_name',mapping:'name'},
			{name:'file_size',mapping:'size',type:'int'},
			'type',
			{name:'lastmod',mapping:'datelastmodified',type:'date'},
			{name:'file_attributes',mapping:'attributes'},
			'mode',
			'directory'
		]),
		listeners:{
			beforeload:{
				fn: function(store, options){
					if (options.startPath && (options.startPath.length > 0)){
						store.baseParams.startPath = options.startPath;
					}
				},
				scope:this
			},
			load: {
				fn: function(store,records,options){
					console.log(records);
				}
			},
			scope:this
		}
	});
	ourStore.load();
});