package fr.esaip.tvshowapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import fr.esaip.tvshowapp.data.UiState
import fr.esaip.tvshowapp.data.model.Episode
import fr.esaip.tvshowapp.data.model.TvShowXX
import fr.esaip.tvshowapp.ui.theme.DarkBackground
import fr.esaip.tvshowapp.ui.theme.DarkCard
import fr.esaip.tvshowapp.ui.theme.StreamingRed
import fr.esaip.tvshowapp.ui.theme.Purple80
import fr.esaip.tvshowapp.ui.theme.PurpleGrey40
import fr.esaip.tvshowapp.viewmodel.TVShowDetailsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TVShowDetailsScreen(
    tvShowId: Int,
    onBackClick: () -> Unit,
    tvShowDetailsViewModel: TVShowDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(tvShowId) {
        tvShowDetailsViewModel.getTVShowDetails(tvShowId)
    }
    val tvShowDetailsState by tvShowDetailsViewModel.tvShowDetailsState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (tvShowDetailsState) {
                            is UiState.Success -> (tvShowDetailsState as UiState.Success<TvShowXX>).data.name
                            else -> "Détails"
                        },
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkCard
                )
            )
        }
    ) { innerPadding ->
        when (tvShowDetailsState) {
            is UiState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(color = StreamingRed)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Chargement des détails...", color = Color.White)
                }
            }
            is UiState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "❌ Erreur",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = (tvShowDetailsState as UiState.Error).message,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { tvShowDetailsViewModel.getTVShowDetails(tvShowId) }) {
                        Text("Réessayer")
                    }
                }
            }
            is UiState.Success -> {
                val tvShow = (tvShowDetailsState as UiState.Success<TvShowXX>).data
                TVShowDetailsContent(
                    tvShow = tvShow,
                    viewModel = tvShowDetailsViewModel,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun TVShowDetailsContent(
    tvShow: TvShowXX,
    viewModel: TVShowDetailsViewModel,
    modifier: Modifier = Modifier
) {
    val selectedSeason by viewModel.selectedSeason.collectAsStateWithLifecycle()
    val seasons = tvShow.episodes.map { it.season }.distinct().sorted()
    
    val filteredEpisodes = if (selectedSeason != null) {
        tvShow.episodes.filter { it.season == selectedSeason }
    } else {
        tvShow.episodes
    }
    
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            AsyncImage(
                model = tvShow.imagePath,
                contentDescription = tvShow.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        item {
            Column {
                Text(
                    text = tvShow.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Note",
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text(
                        text = "${tvShow.rating}/10 (${tvShow.ratingCount} avis)",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Informations",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Réseau: ${tvShow.network}")
                    Text("Statut: ${tvShow.status}")
                    Text("Pays: ${tvShow.country}")
                    Text("Date de début: ${tvShow.startDate}")
                    Text("Durée: ${tvShow.runtime} min")
                    Text("Genres: ${tvShow.genres.joinToString(", ")}")
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Description",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = tvShow.description)
                }
            }
        }

        item {
            SeasonDropdown(
                seasons = seasons,
                selectedSeason = selectedSeason,
                onSeasonSelected = { viewModel.selectSeason(it) }
            )
        }

        item {
            Text(
                text = if (selectedSeason != null) {
                    "Épisodes de la saison $selectedSeason (${filteredEpisodes.size})"
                } else {
                    "Tous les épisodes (${tvShow.episodes.size})"
                },
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        items(filteredEpisodes) { episode ->
            EpisodeCard(episode = episode)
        }
    }
}

@Composable
fun EpisodeCard(episode: Episode) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "S${episode.season}E${episode.episode} - ${episode.name}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Diffusé le: ${episode.airDate}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun SeasonDropdown(
    seasons: List<Int>,
    selectedSeason: Int?,
    onSeasonSelected: (Int?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Filtrer par saison",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (selectedSeason != null) "Saison $selectedSeason" else "Toutes les saisons",
                    modifier = Modifier.weight(1f)
                )
                Text("▼")
            }
            
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Toutes les saisons") },
                    onClick = {
                        onSeasonSelected(null)
                        expanded = false
                    }
                )
                seasons.forEach { season ->
                    DropdownMenuItem(
                        text = { Text("Saison $season") },
                        onClick = {
                            onSeasonSelected(season)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
