package com.watcha.watchapedia.service;

import com.watcha.watchapedia.model.entity.Character;
import com.watcha.watchapedia.model.network.Header;
import com.watcha.watchapedia.model.network.request.CharacterApiRequest;
import com.watcha.watchapedia.model.network.response.CharacterApiResponse;
import com.watcha.watchapedia.model.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharacterApiLogicService extends BaseService<CharacterApiRequest, CharacterApiResponse, Character>{
    private final CharacterRepository characterRepository;

    private CharacterApiResponse response(Character character){
        CharacterApiResponse characterApiResponse = CharacterApiResponse.builder()
                .perIdx(character.getPerIdx())
                .perName(character.getPerName())
                .perPhoto(character.getPerPhoto())
                .perRole(character.getPerRole())
                .perBiography(character.getPerBiography())
                .perBook(character.getPerBook())
                .perMov(character.getPerMov())
                .perTv(character.getPerTv())
                .perWebtoon(character.getPerWebtoon())
                .build();
        return characterApiResponse;
    }

    @Override
    public Header<CharacterApiResponse> create(Header<CharacterApiRequest> request) {
        CharacterApiRequest characterApiRequest = request.getData();
        Character character = Character.builder()
                .perName(characterApiRequest.getPerName())
                .perPhoto(characterApiRequest.getPerPhoto())
                .perBiography(characterApiRequest.getPerBiography())
                .build();
        Character newCharacter = baseRepository.save(character);
        return Header.OK(response(newCharacter));
    }



    @Override
    public Header<CharacterApiResponse> read(Long id) {
        return baseRepository.findById(id).map(character -> response(character))
                .map(Header::OK).orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<CharacterApiResponse> update(Header<CharacterApiRequest> request) {
        CharacterApiRequest characterApiRequest = request.getData();
        Optional<Character> characters = characterRepository.findByPerIdx(characterApiRequest.getPerIdx());
        System.out.println(characters);
        return characters.map(
                        character -> {
                            System.out.println(request.getData().getPerTv());
                            System.out.println(request.getData().getPerBook());
                            System.out.println(request.getData().getPerMovie());
                            System.out.println(request.getData().getPerWebtoon());

                            if (request.getData().getPerTv()!=null){
                                if(character.getPerTv()==null){
                                    character.setPerTv(characterApiRequest.getPerTv());
                                }else{
                                    character.setPerTv(character.getPerTv()+","+characterApiRequest.getPerTv());
                                }
                            }else if(request.getData().getPerBook()!=null){
                                if(character.getPerBook()==null){
                                    character.setPerBook(characterApiRequest.getPerBook());
                                }else{
                                    character.setPerBook(character.getPerBook()+","+characterApiRequest.getPerBook());
                                }
                            }else if(request.getData().getPerMovie()!=null){
                                if(character.getPerMov()==null){
                                    character.setPerMov(characterApiRequest.getPerMovie());
                                }else{
                                    character.setPerMov(character.getPerMov()+","+characterApiRequest.getPerMovie());
                                }
                            }else if(request.getData().getPerWebtoon()!=null){
                                if(character.getPerWebtoon()==null){
                                    character.setPerWebtoon(characterApiRequest.getPerWebtoon());
                                }else{
                                    character.setPerWebtoon(character.getPerTv()+","+characterApiRequest.getPerWebtoon());
                                }
                            }else{
                                if(characterApiRequest.getPerBiography()!=null){character.setPerBiography(characterApiRequest.getPerBiography());}
                                if(characterApiRequest.getPerName()!=null){character.setPerName(characterApiRequest.getPerName());}
                                if(characterApiRequest.getPerPhoto()!=null){character.setPerPhoto(characterApiRequest.getPerPhoto());}
                            }

                            return character;
                        }).map(character -> characterRepository.save(character))
                .map(character -> response(character))
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음")
                );
    }

    @Override
    public Header delete(Long perIdx) {
        Optional<Character> character = baseRepository.findById(perIdx);
        return character.map(character1 -> {
            baseRepository.delete(character1);
            return Header.OK();
        }).orElseGet(() -> Header.ERROR("데이터 없음"));
    }


    public Header<List<CharacterApiResponse>> search(){
        List<Character> characters = baseRepository.findAll();
        List<CharacterApiResponse> characterApiResponse = characters.stream().map(
                character -> response(character)).collect(Collectors.toList());


        return Header.OK(characterApiResponse);
    }


    public List<Character> characterList(){
        List<Character> characters = characterRepository.findAll();
        for(Character c : characters){
            if(c.getPerPhoto() == null){
                c.setPerPhoto("data:image/jpeg;base64,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");
            }
        }
        return characters;
    }
}


