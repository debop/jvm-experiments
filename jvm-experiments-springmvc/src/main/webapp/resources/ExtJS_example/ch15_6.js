Ext.onReady(function () {
    var recDefinition = [
        {name: 'authorID', type: 'int'},
        'firstName',
        'lastName',
        'bio'
    ];
    var ourReader = new Ext.data.CFQueryReader({idProperty: 'authorID', root: 'query'}, recDefinition);
    var ourWriter = new Ext.data.JsonWriter({listful: true, writeAllFields: true});
    var ourProxy = new Ext.data.HttpProxy({
        api: {
            read: 'ch15Example.cfc?method=GetAuthors&returnFormat=JSON',
            update: 'ch15Example.cfc?method=UpdateAuthors&returnFormat=JSON'
        }
    });
    var ourStore = new Ext.data.Store({
        proxy: ourProxy,
        writer: ourWriter,
        reader: ourReader,
        autoSave: false,
        listeners: {
            save: {
                fn: function (store, batch, data) {
                    store.commitChanges();
                },
                scope: this
            }
        }
    });
    var fm = Ext.form;
    var ourGrid = new Ext.grid.EditorGridPanel({
        store: ourStore,
        cm: new Ext.grid.ColumnModel({
            columns: [
                {
                    id: 'authorID',
                    dataIndex: 'authorID',
                    width: 40
                },
                {
                    id: 'firstName',
                    dataIndex: 'firstName',
                    header: 'First Name',
                    width: 150,
                    editor: new fm.TextField({
                        allowBlank: false
                    })
                },
                {
                    id: 'lastName',
                    dataIndex: 'lastName',
                    header: 'Last Name',
                    width: 150,
                    editor: new fm.TextField({
                        allowBlank: false
                    })
                },
                {
                    id: 'bio',
                    dataIndex: 'bio',
                    header: 'Bio',
                    width: 350,
                    editor: new fm.TextField()
                }
            ]
        }),
        renderTo: 'ch15_6',
        width: 750,
        height: 600,
        title: 'Authors',
        frame: true,
        clicksToEdit: 1,
        bbar: [
            {
                text: 'Save Changes',
                handler: function () {
                    ourStore.save();
                }
            }
        ]
    });
    ourStore.load();
});