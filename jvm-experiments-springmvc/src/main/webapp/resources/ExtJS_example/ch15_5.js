Ext.onReady(function () {
    var recDefinition = [
        {name: 'authorID', type: 'int'},
        'firstName',
        'lastName',
        'bio'
    ];
    var ourReader = new Ext.data.CFQueryReader({idProperty: 'authorID', root: 'query'}, recDefinition);
    var ourStore = new Ext.data.Store({
        url: 'ch15Example.cfc',
        baseParams: {
            method: 'GetAuthors',
            returnFormat: 'JSON',
            queryFormat: 'column'
        },
        reader: ourReader,
        listeners: {
            load: {
                fn: function (store, records, options) {
                    console.log(records);
                }
            },
            scope: this
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
        renderTo: 'ch15_5',
        width: 750,
        height: 600,
        title: 'Authors',
        frame: true,
        clicksToEdit: 1
    });
    ourStore.load();
});