package kirill.industries;

import kirill.industries.dto.CompanyDto;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        CompaniesNamesReader reader = new CompaniesNamesReader();

        new IndustriesService(reader).joinIndustries().forEach(company -> {
            System.out.println(company.getName() + " " + company.getIndustry());
        });
    }
}
