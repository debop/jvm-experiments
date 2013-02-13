Ext.namespace('CRM.panels');
CRM.panels.UserDetail = Ext.extend(Ext.Panel,{
	width: 350,
	height: 125,
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
	tpl: new Ext.Template([
		'<img src="images/s.gif" width="21" height="16"/><b>{FIRSTNAME} {LASTNAME}</b><br />',
		'<img src="images/database_edit.gif" width="16" height="16" id="emailEdit_{ID}" class="iconLnk" align="Edit Email Address" border="0" />{EMAIL}<br />',
		'<img src="images/database_edit.gif" width="16" height="16" id="phoneEdit_{ID}" class="iconLnk" align="Edit Phone" border="0" />{PHONE} ({PHONETYPE})<br />'
	]),
	initComponent: function(){
		CRM.panels.UserDetail.superclass.initComponent.call(this);
		if (typeof this.tpl === 'string') {
			this.tpl = new Ext.XTemplate(this.tpl);
		}
	},
	update: function(data) {
		this.data = data;
		this.tpl.overwrite(this.body, this.data);
	}
});
Ext.reg('userdetail',CRM.panels.UserDetail);