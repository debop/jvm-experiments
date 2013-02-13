Ext.namespace('CRM.panels');
CRM.panels.AddressDetail = Ext.extend(Ext.Panel,{
	width:350,
	height:125,
	bodyStyle: 'padding:10px',
	data: {
		ID: 0,
		FIRSTNAME: '',
		LASTNAME: '',
		EMAIL: '',
		ADDRESSTYPE: 'Home (mailing)',
		STREET1: '',
			STREET2: '',
		STREET3: '',
		CITY: '',
		STATE: '',
		ZIP: '',
		PHONETYPE: 'Home',
		PHONE: ''
	},
	split: false,
	tpl: new Ext.XTemplate([
		'<b>{ADDRESSTYPE} Address</b><br />',
		'<img src="images/database_edit.gif" width="16" height="16" id="addrEdit_{ID}" class="iconLnk" align="Edit Address" border="0" />{STREET1}<br />',
		'<tpl if="STREET2.length &gt; 0">',
		'<img src="images/s.gif" width="21" height="16"/>{STREET2}<br />',
		'</tpl>',
		'<tpl if="STREET3.length &gt; 0">',
		'<img src="images/s.gif" width="21" height="16"/>{STREET3}<br />',
		'</tpl>',
		'<img src="images/s.gif" width="21" height="16"/>{CITY}, {STATE} {ZIP}'
	]),
	initComponent: function(){
		CRM.panels.AddressDetail.superclass.initComponent.call(this);
		if (typeof this.tpl === 'string') {
			this.tpl = new Ext.XTemplate(this.tpl);
		}
	},
	update: function(data) {
		this.data = data;
		this.tpl.overwrite(this.body, this.data);
	}
});
Ext.reg('addrdetail',CRM.panels.AddressDetail);