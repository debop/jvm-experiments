Ext.onReady(function () {
    var formPanel = new Ext.form.FormPanel({
        title: 'HTML 에디터 예제',
        applyTo: 'ch17_3',
        layout: 'form',
        labelAlign: 'top',
        width: 600,
        autoHeight: true,
        frame: true,
        items: [
            {
                xtype: 'htmleditor',
                id: 'bio',
                fieldLabel: '블로그 엔트리 ',
                height: 200,
                anchor: '98%'
            }
        ]
    });
});