# How to format code withh spotless

install in pom.xml
<plugin>
    <groupId>com.diffplug.spotless</groupId>
    <artifactId>spotless-maven-plugin</artifactId>
    <version>2.43.0</version>
    <configuration>
        <java>
            <removeUnusedImports />
            <toggleOffOn/>
            <trimTrailingWhitespace/>
            <endWithNewline/>
            <indent>
                <tabs>true</tabs>
                <spacesPerTab>4</spacesPerTab>
            </indent>
            <palantirJavaFormat/>
            <importOrder>
                <order>java,jakarta,org,com,com.diffplug,</order>
            </importOrder>
        </java>
    </configuration>
    <executions>
        <execution>
            <phase>compile</phase>
            <goals>
                <goal>apply</goal>
            </goals>
        </execution>
    </executions>
</plugin>

... explain each field

how to run

mvn spotless:check
mvn spotless:apply