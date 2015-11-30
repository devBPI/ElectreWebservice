
# Install
You'll need **java 1.8** and **gradle** to build the project: `apt-get gradle`.

# Building
You can build a fat jar by running the `gradle jar` command.
If your are running the command behind a proxy don't forget to add the correct configuration while running the command:
`gradle -Dhttp.proxyHost=host  -Dhttp.proxyPort=port -Dhttps.proxyHost=host  -Dhttps.proxyPort=port jar`

# Launch
To launch the compiled jar:
`ELECTRE_KEY=client_key ELECTRE_SECRET=secret_password  java ElectreConnector-version.jar`

- If you are using a proxy, launch the jar with:    
`ELECTRE_KEY=client_key ELECTRE_SECRET=secret_password java -Dhttp.proxyHost=host  -Dhttp.proxyPort=port -jar ElectreConnector-version.jar`

by default the server is launched on `0.0.0.0:8080` and respond to the following path:

- `/imagette/ean`
- `/couverture/ean`
- `/tabledesmatieres/ean`
- `/quatrieme/ean`