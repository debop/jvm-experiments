Ext.onReady(function () {
    var example2 = new Ext.Panel({
        applyTo: 'ch15_2',
        title: '15장 예제 2',
        width: 250,
        height: 250,
        frame: true,
        autoLoad: {
            url: 'ch15Example.cfc',
            params: {
                method: 'example2',
                returnFormat: 'plain',
                id: 1
            }
        }
    });
});