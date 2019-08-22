package com.springinaction.test3;

import com.springinaction.spittr.SpitrrController;
import com.springinaction.spittr.Spittle;
import com.springinaction.spittr.SpittleRepository;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class SpitrrControllerTest {

//        @Test
    public void shouldShowRecentSpittles() throws Exception {
        List<Spittle> spittleList = createSpittles(20);
        SpittleRepository mockRepository = mock(SpittleRepository.class);
        when(mockRepository.findSpittles(Long.MAX_VALUE, 20)).thenReturn(spittleList);
        SpitrrController spitrrController = new SpitrrController(mockRepository);
        MockMvc mockMvc = standaloneSetup(spitrrController).setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp")).build();
        mockMvc.perform(get("/spittles/test")).andExpect(view().name("spittlesPage")).andExpect(model().attributeExists("spittleList"));
    }

    @Test
    public void shouldShowSpittles() throws Exception {
        List<Spittle> spittleList = createSpittles(50);
        SpittleRepository mockRepository = mock(SpittleRepository.class);
        when(mockRepository.findSpittles(238900, 50)).thenReturn(spittleList);
        SpitrrController spitrrController = new SpitrrController(mockRepository);
        MockMvc mockMvc = standaloneSetup(spitrrController).setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp")).build();
        mockMvc.perform(get("/spittles?max=238900&count=50")).andExpect(view().name("spittles"))
                .andExpect(model().attributeExists("spittleList"))
                .andExpect(model().attribute("spittleList", hasItems(spittleList.toArray())));
    }

    @Test
    public void shouldFindOne() throws Exception {
        Spittle spittle = new Spittle(1l,"random one");
        SpittleRepository mockRepository = mock(SpittleRepository.class);
        when(mockRepository.findOne(1l)).thenReturn(spittle);
        SpitrrController spitrrController = new SpitrrController(mockRepository);
        MockMvc mockMvc = standaloneSetup(spitrrController).setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp")).build();
        mockMvc.perform(get("/spittles/find/1")).andExpect(view().name("spittle"))
                .andExpect(model().attributeExists("spittle"))
                .andExpect(model().attribute("spittle", spittle));
    }

//    @Test
//    public void shouldRegister(){
//
//        verify();
//        atLeastOnce()
//
//    }



    public List<Spittle> createSpittles(int count) {
        List<Spittle> spittleList = new ArrayList<Spittle>();
        for (int i = 0; i < count; i++) {
            spittleList.add(new Spittle(Long.valueOf(i), "this is the " + i));
        }
        return spittleList;
    }
}
