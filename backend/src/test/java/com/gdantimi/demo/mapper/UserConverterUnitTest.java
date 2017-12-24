package com.gdantimi.demo.mapper;

import com.gdantimi.demo.model.dto.UserDto;
import com.gdantimi.demo.model.entity.Sector;
import com.gdantimi.demo.model.entity.User;
import com.gdantimi.demo.service.SectorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.lang.Boolean.TRUE;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserConverterUnitTest {

    @Mock
    private SectorService sectorService;

    @InjectMocks
    private UserConverter converter;

    @Test
    public void convertTo_shouldMapAllFields() {
        long id = 1L;
        String name = "name";
        List<Long> sectorsIds = asList(1L, 2L);
        UserDto userDto = UserDto.builder()
                .id(id)
                .name(name)
                .sectorsIds(sectorsIds)
                .termsAgreed(TRUE)
                .build();

        List<Sector> sectors = asList(Sector.builder().id(1L).build(), Sector.builder().id(2L).build());
        when(sectorService.findAllById(eq(sectorsIds))).thenReturn(sectors);

        User user = converter.convertTo(userDto, null);
        assertThat(user, allOf(
                hasProperty("id", nullValue()),
                hasProperty("name", equalTo(name)),
                hasProperty("sectors", contains(
                        hasProperty("id", equalTo(1L)),
                        hasProperty("id", equalTo(2L))
                )),
                hasProperty("termsAgreed", equalTo(TRUE))
        ));
    }


    @Test
    public void convertTo_shouldReturnNullWhenInputValueIsNull() {
        User user = converter.convertTo(null, null);
        assertThat(user, nullValue());
    }

    @Test
    public void convertFrom_shouldMapAllFields() {
        long id = 1L;
        String name = "name";
        List<Long> sectorsIds = asList(1L, 2L);
        List<Sector> sectors = asList(Sector.builder().id(1L).build(), Sector.builder().id(2L).build());
        User user = User.builder()
                .id(id)
                .name(name)
                .sectors(sectors)
                .termsAgreed(TRUE)
                .build();

        when(sectorService.findAllById(eq(sectorsIds))).thenReturn(sectors);

        UserDto userDto = converter.convertFrom(user, null);
        assertThat(userDto, allOf(
                hasProperty("id", equalTo(id)),
                hasProperty("name", equalTo(name)),
                hasProperty("sectorsIds", contains(1L, 2L)),
                hasProperty("termsAgreed", equalTo(TRUE))
        ));
    }

    @Test
    public void convertFrom_shouldReturnNullWhenInputValueIsNull() {
        UserDto userDto = converter.convertFrom(null, null);
        assertThat(userDto, nullValue());
    }
}