package com.watcha.watchapedia.service;

import com.watcha.watchapedia.model.entity.Person;
import com.watcha.watchapedia.model.network.Header;
import com.watcha.watchapedia.model.network.request.PersonApiRequest;
import com.watcha.watchapedia.model.network.response.PersonApiResponse;
import com.watcha.watchapedia.model.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonApiLogicService extends BaseService<PersonApiRequest, PersonApiResponse, Person>  {

    private final PersonRepository personRepository;

    private final MovieApiLogicService movieApiLogicService;
    private final TvApiLogicService tvApiLogicService;
    private final BookApiLogicService bookApiLogicService;
    private final WebtoonApiLogicService webtoonApiLogicService;

    private final GlobalMethodService globalMethodService;


    public Header<List<PersonApiResponse>> search(String searchKey){
        System.out.println("logicService에 search메소드 들어옴! 받아온 검색어 : " + searchKey);
        List<Person> persons = personRepository.findByPerNameContaining(searchKey);
        System.out.println("findByPerNameContaining의 결과 : "+persons);
        List<PersonApiResponse> personApiResponses= persons.stream().map(person -> globalMethodService.serachResponse(person)).collect(Collectors.toList());
        return Header.OK(personApiResponses);
    }
    @Override
    public Header<PersonApiResponse> create(Header<PersonApiRequest> request) {
        return null;
    }

    @Override
    public Header<PersonApiResponse> read(Long id) {
        return null;
    }

    @Override
    public Header<PersonApiResponse> update(Header<PersonApiRequest> request) {
        return null;
    }

    @Override
    public Header<PersonApiResponse> delete(Long id) {
        return null;
    }
}
