package li.ma.mchart.main;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public enum  CharterGroup {

    GROUP1("测试组",new ArrayList<Charter>());

    private String name;

    private List<Charter> charterList;


}
