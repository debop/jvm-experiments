Ext.onReady(function () {
    var cls = "Band";
    var member = {
        firstName: 'Eric',
        lastName: 'Clapton',
        position: '리드 기타 연주가'
    };
    var pnl = new Ext.Panel({
        applyTo: 'ch17_4',
        width: 250,
        autoHeight: true,
        bodyStyle: 'padding:5px',
        title: '밴드 멤버',
        html: String.format('<div class=\'{0}\'>{2}, {1}: {3}</div>',
            cls, member.firstName, member.lastName, member.position)
    });
});