Ext.onReady(function () {
    var userData = [
        {ID: 1, FIRSTNAME: 'John', LASTNAME: 'Lennon', EMAIL: 'john@beatles.com', PASSWORD: 'apple1', ADDRESSTYPE: 'Home (Mailing)', STREET1: '117 Abbey Road', STREET2: '', STREET3: '', CITY: 'New York', STATE: 'NY', ZIP: '12345', PHONETYPE: 'Cell', PHONE: '123-456-7890'},
        {ID: 2, FIRSTNAME: 'Paul', LASTNAME: 'McCartney', EMAIL: 'paul@beatles.com', PASSWORD: 'linda', ADDRESSTYPE: 'Work(Mailing)', STREET1: '108 Penny Lane', STREET2: '', STREET3: '', CITY: 'Los Angeles', STATE: 'CA', ZIP: '67890', PHONETYPE: 'Home', PHONE: '456-789-0123'},
        {ID: 3, FIRSTNAME: 'George', LASTNAME: 'Harrison', EMAIL: 'george@beatles.com', PASSWORD: 'timebandit', ADDRESSTYPE: 'Home (Shipping)', STREET1: '302 SpaceWay', STREET2: '', STREET3: '', CITY: 'Billings', STATE: 'MT', ZIP: '98765', PHONETYPE: 'Office', PHONE: '890-123-4567'},
        {ID: 4, FIRSTNAME: 'Ringo', LASTNAME: 'Starr', EMAIL: 'bignose@beatles.com', PASSWORD: 'barbie', ADDRESSTYPE: 'Home (Mailing)', STREET1: '789 Zildizhan Pl', STREET2: '', STREET3: '', CITY: 'Malibu', STATE: 'CA', ZIP: '43210', PHONETYPE: 'Home', PHONE: '567-890-1234'}
    ];

    var userDetail = new Ext.Panel({
        applyTo: 'ch13_1',
        width: 350,
        autoHeight: true,
        title: '13장 예제 1',
        bodyStyle: 'padding:10px',
        data: userData[0],
        tpl: new Ext.XTemplate([
            '<img src="images/s.gif" width="21" height="16"/><b>{FIRSTNAME} {LASTNAME}</b><br />',
            '<img src="images/database_edit.gif" width="16" height="16" id="emailEdit_{ID}" class="iconLnk" align="EditEmail Address" border="0" />{EMAIL}<br />',
            '<img src="images/database_edit.gif" width="16" height="16" id="phoneEdit_{ID}" class="iconLnk" align="EditPhone" border="0" />{PHONE} ({PHONETYPE})<br />',
            '<b>{ADDRESSTYPE} Address</b><br />',
            '<img src="images/database_edit.gif" width="16" height="16" id="addrEdit_{ID}" class="iconLnk" align="EditAddress" border="0" />{STREET1}<br />',
            '<tpl if="STREET2.length &gt; 0">',
            '<img src="images/s.gif" width="21" height="16"/>{STREET2}<br />',
            '</tpl>',
            '<tpl if="STREET3.length &gt; 0">',
            '<img src="images/s.gif" width="21" height="16"/>{STREET3}<br />',
            '</tpl>',
            '<img src="images/s.gif" width="21" height="16"/>{CITY}, {STATE} {ZIP}'
        ])
    });
});