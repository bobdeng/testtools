# maven 
```
 <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
    <dependency>
            <groupId>com.github.bobdeng</groupId>
            <artifactId>testtools</artifactId>
            <version>1.5</version>
        </dependency>
```

# 用法

```
assertThat(something, snapshotMatch(this, "snapshot_name"));
```
