Ext.onReady(function () {
    var formPanel = new Ext.form.FormPanel({
        title: '날짜 필드 예제',
        applyTo: 'ch17_1',
        layout: 'form',
        labelAlign: 'top',
        width: 210,
        autoHeight: true,
        frame: true,
        items: [
            {
                xtype: 'datefield',
                fieldLabel: '생일',
                name: 'dob',
                width: 190,
                allowBlank: false
            }
        ]
    });
});