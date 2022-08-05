package kirill.industries;

import kirill.industries.dto.CompaniesResponse;
import kirill.industries.dto.CompanyDto;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.Closeable;
import java.util.*;
import java.util.stream.Collectors;

public class IndustriesService {
    private static final String YAHOO_URL = "https://finance.yahoo.com/api/companies";
    private static final String INDEED_URL = "https://www.indeed.com/api/companies";

    private WebClient webClient = WebClient.create();

    private CompaniesNamesReader reader;

    public IndustriesService(CompaniesNamesReader reader) {
        this.reader = reader;
    }

    public List<CompanyDto> joinIndustries() {
        //читаем из файла список имён 50 компаний у которых на 2-ух сайтах отличаются названия индустрий
        List<String> companiesNames = reader.readCompaniesNames();

        //с каждого сайта получаем список этих 50 компаний и храним в виде мапы
        //ключ - это имя компании, значение - объект компании
        Map<String, CompanyDto> yahooCompaniesMap = makeHttpRequest(YAHOO_URL, companiesNames);
        Map<String, CompanyDto> indeedCompaniesMap = makeHttpRequest(INDEED_URL, companiesNames);

        List<CompanyDto> updatedCompaniesList = new ArrayList<>();

        if (yahooCompaniesMap.size() == 50 && indeedCompaniesMap.size() == 50){

            // из обеих мап получаем названия индустрий одной и той же компании и объединяем
            yahooCompaniesMap.entrySet().stream().forEach(entry -> {
                String indeedIndustry = indeedCompaniesMap.get(entry.getKey()).getIndustry();
                String yahooIndustry = entry.getValue().getIndustry();
                String newIndustry = new StringBuilder()
                        .append(yahooIndustry)
                        .append(", ")
                        .append(indeedIndustry).toString();
                updatedCompaniesList.add(new CompanyDto(entry.getKey(), newIndustry ));
            });

        }

        //возвращаем обновленный список компаний с объединенными индустриями
        return updatedCompaniesList;
    }

    private Map<String, CompanyDto> makeHttpRequest(String uri, List<String> companiesNames) {
        CompaniesResponse response = webClient.get()
                .uri(uriBuilder -> {
                    return uriBuilder.path(uri)
                            .queryParams(convertListToHttpParamsMap(companiesNames))  //список компаний добавляем в параметры в url
                            .build();
                })
                .retrieve()
                .bodyToMono(CompaniesResponse.class)
                .block();

        return response.getCompanies().stream()
                .collect(Collectors.toMap(CompanyDto::getName, companyDto -> companyDto));
    }

    private MultiValueMap<String, String> convertListToHttpParamsMap(List<String> companies) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.addAll("company_name", companies);
        return multiValueMap;
    }
}
