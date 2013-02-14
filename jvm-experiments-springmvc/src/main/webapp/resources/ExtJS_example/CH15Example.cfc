<!---
/ METHOD: getDirectoryContents
/
/ @param startPath:string
/ @param recurse:boolean (optional)
/ @param fileFilter:string (optional)
/ @param dirFilter:string (optional - File|Dir)
/ @param sortField:string (optional -NAME|SIZE|TYPE|DATELASTMODIFIED|ATTRIBUTES|MODE|DIRECTORY)
/ @param sortDirection:string (option - ASC|DESC[defaults to ASC])
/ @return retQ:query
--->
<cffunction name="getDirectoryContents" access="remote" output="false" returntype="query">
    <cfargument name="startPath" required="true" type="string"/>
    <cfargument name="recurse" required="false" type="boolean" default="false"/>
    <cfargument name="sortDirection" required="false" type="string" default="ASC"/>
<!--- Set some function local variables --->
    <cfset var q = ""/>
    <cfset var retQ = ""/>
    <cfset var attrArgs = {}/>
    <cfset var ourDir = ExpandPath(ARGUMENTS.startPath)/>
<!--- Create some lists of valid arguments --->
    <cfset var filterList = "File,Dir"/>
    <cfset var sortDirList = "ASC,DESC"/>
    <cfset var columnList = "NAME,SIZE,TYPE,DATELASTMODIFIED,ATTRIBUTES,MODE,DIRECTORY"/>
    <cftry>
        <cfset attrArgs.recurse = ARGUMENTS.recurse/>
<!--- Verify the directory exists before continuing --->
        <cfif DirectoryExists(ourDir)>
            <cfset attrArgs.directory = ourDir/>
            <cfelse>
            <cfthrow type="Custom" errorcode="Our_Custom_Error" message="The directory you are trying to reach does not exist."/>
        </cfif>
<!--- Conditionally apply some optional filtering and sorting --->
        <cfif IsDefined("ARGUMENTS.fileFilter")>
            <cfset attrArgs.filter = ARGUMENTS.fileFilter/>
        </cfif>
        <cfif IsDefined("ARGUMENTS.sortField")>
            <cfif ListFindNoCase(columnList, ARGUMENTS.sortField)>
                <cfset attrArgs.sort = ARGUMENTS.sortField & " " & ARGUMENTS.sortDirection/>
                <cfelse>
                <cfthrow type="custom" errorcode="Our_Custom_Error" message="You have chosen an invalid sort field. Please use one of the following: " & columnList />
            </cfif>
        </cfif>
        <cfdirectory action="list" name="q" attributeCollection="#attrArgs#"/>
<!--- If there are files and/or folders, and you want to sort by TYPE --->
        <cfif q.recordcount and IsDefined("ARGUMENTS.dirFilter")>
            <cfif ListFindNoCase(filterList, ARGUMENTS.dirFilter)>
                <cfquery name="retQ" dbtype="query">
            SELECT #columnList#
            FROM q
            WHERE TYPE =
                    <cfqueryparam cfsqltype=" cf_sql_varchar" value="#ARGUMENTS.dirFilter#" maxlength="4"/>
                </cfquery>
                <cfelse>
                <cfthrow type="Custom" errorcode="Our_Custom_Error" message="You have passed an invalid dirFilter. The only accepted values are File and Dir."/>
            </cfif>
            <cfelse>
            <cfset retQ = q/>
        </cfif>
        <cfcatch type="any">
<!--- Place Error Handler Here --->
        </cfcatch>
    </cftry>
    <cfreturn retQ/>
</cffunction>

<!--- METHOD: GetAuthors --->
<cffunction name="GetAuthors" access="remote" output="false" returntype="struct">
    <cfset var retVal = StructNew()/>
    <cfset var q = ""/>
    <cfset retVal['success'] = true/>
    <cftry>
        <cfquery name="q" datasource="cfbookclub">
			SELECT authorID,
			firstName,
			lastName,
			bio
			FROM Authors
		</cfquery>
        <cfif q.recordcount>
            <cfset retVal['query'] = q/>
        </cfif>
        <cfcatch type="any">
<!--- Error Handling Here --->
            <cfset retVal['success'] = false/>
            <cfset retVal['errorMsg'] = "There was an error retrieving records."/>
        </cfcatch>
    </cftry>
    <cfreturn retVal/>
</cffunction>

<!--- METHOD: UpdateAuthors --->
<cffunction name="UpdateAuthors" access="remote" output="false" returntype="boolean">
    <cfargument name="query" required="true" type="string"/>
    <cfset var retVal = StructNew()/>
    <cfset var q = ""/>
    <cfset var i = 0/>
    <cfset ARGUMENTS.query = '{"data":' & ARGUMENTS.query & '}'/>
    <cfset pairs = DeserializeJson(ARGUMENTS.query)/>
    <cfset retVal["success"] = true/>
    <cfloop array="#pairs.data#" index="i">
        <cftry>
            <cfquery name="q" datasource="cfbookclub">
				UPDATE Authors
				SET firstName = '#i.firstName#',
				lastName = '#i.lastName#',
				bio = '#i.bio#'
				WHERE authorID = #Val(i.authorID)#
            </cfquery>
            <cfcatch type="any">
<!--- Error Handling Here --->
                <cfset retVal['errorMsg'] = "There was an error"/>
                <cfset retVal['success'] = false/>
            </cfcatch>
        </cftry>
    </cfloop>
    <cfreturn retVal["success"]/>
</cffunction>