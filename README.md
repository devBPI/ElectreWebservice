
# Install
You'll need **java 1.8** and **gradle** to build the project: `apt-get gradle`.

# Building
You can build a fat jar by running the `gradle jar` command.

# Launch
To launch the compiled jar:
`java ElectreConnector-version.jar`

- If you are using a proxy, launch the jar with:
`java  -Dhttp.proxyHost=10.1.2.30  -Dhttp.proxyPort=3128 -jar ElectreConnector-version.jar`
