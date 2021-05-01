package com.liadov.cat.lesson10.task2;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SausageControllerWithFor - class for writing and extracting Sausage objects to file with for
 *
 * @author Aleksandr Liadov on 4/26/2021
 */
@Slf4j
public class SausageControllerWithFor {
    private final String replacePattern = "^Sausage\\(type=|, weight=|, cost=|\\)";
    private Path defaultFilePath = Paths.get("src/main/resources/defaultSausageFile.txt");

    /**
     * Method write Sausage objects to target file
     *
     * @param path        Ptah target file. Replace with defaultFilePath in case provided Path is null
     * @param sausageList List<Sausage> collection of objects to be written to file
     */
    public void writeListToFile(Path path, List<Sausage> sausageList) {
        Optional<Path> optionalPath = Optional.ofNullable(path);
        Optional<List<Sausage>> optionalSausageList = Optional.ofNullable(sausageList);
        log.trace("write Sausages to file initiated. Path = {}, List = {}", optionalPath, sausageList);

        if (optionalSausageList.isPresent()) {
            List<String> stringList = Stream.empty().map(String::valueOf).collect(Collectors.toList());
            for (Sausage sausage : optionalSausageList.get()) {
                stringList.add(new String(Base64.getEncoder().encode(sausage.toString().getBytes())));
            }
            try {
                Files.write(optionalPath.orElse(defaultFilePath), stringList);
                log.debug("write operation executed successfully");
            } catch (IOException e) {
                log.error("File operation FAILED", e);
            }
        }
    }


    /**
     * Method reads Sausage objects from target file and returns List<Sausage>
     *
     * @param path Path target file.
     * @return List<Sausage> collection of created objects
     */
    public List<Sausage> readObjectsFromFile(Path path) {
        List<Sausage> sausagesFromFile = Stream.empty().map((x) -> (Sausage) x).collect(Collectors.toList());
        Optional<Path> optionalPath = Optional.ofNullable(path);
        log.trace("initiated read Sausage objects from file. Path = {}", optionalPath);
        if (optionalPath.isPresent()) {
            List<String> stringList = Stream.empty().map(String::valueOf).collect(Collectors.toList());
            try {
                stringList = Files.lines(path).collect(Collectors.toList());
            } catch (IOException e) {
                log.error("Error", e);
            }
            for (String stringSausage : stringList) {
                String[] values = new String(Base64.getDecoder().decode(stringSausage)).replaceAll(replacePattern, " ")
                        .trim()
                        .split(" ");
                log.trace("values before parsing{}", Arrays.toString(values));
                sausagesFromFile.add(new Sausage(values[0], Integer.parseInt(values[1]), Long.parseLong(values[2])));
            }
        }
        log.trace("received objects from file: {}", sausagesFromFile);
        return sausagesFromFile;
    }
}