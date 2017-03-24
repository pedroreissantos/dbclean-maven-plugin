
	Maven plugin to clean the application database tables.

compile: mvn install

run: mvn pt.tecnico.plugin:dbclean-maven-plugin:1.0-SNAPSHOT:dbclean

to run as: mvn dbclean:dbclean

add to ~/.m2/settings.xml

	<pluginGroups>

		<pluginGroup>pt.tecnico.plugin</pluginGroup>

	</pluginGroups>

if ~/.m2/settings.xml does not exist, copy ./seetings.xml to it.

(C) reis.santos(at)tecnico.ulisboa.pt 24mar2017
