How to run:
1. Add lire java lib to project.
2. Config service port in resource/application.properties
3. Run Indexer to index images:
	Ex: mvn exec:java8  -Dexec.mainClass="net.semanticmetadata.lire.sampleapp.Indexer"  -Dexec.args="data/enbac_dress"

4. Add frontend lib
```
cd src/main/resources/static
install npm: http://blog.teamtreehouse.com/install-node-js-npm-windows
npm install bower
bower install

``
3. Run Service:
	Ex: mvn exec:java8  -Dexec.mainClass="service.WebService"