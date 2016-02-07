
  Maven plugin to clean the application database tables.

  compile: mvn install

  run: mvn pt.tecnico.plugin:dbclean-maven-plugin:1.0-SNAPSHOT:dbclean

  to run as: mvn dbclean:dbclean

  add to ~/.m2/settings.xml

  <pluginGroups>

    <pluginGroup>pt.tecnico.plugin</pluginGroup>

  </pluginGroups>

  (C) reis.santos(at)tecnico.ulisboa.pt 7feb2016
