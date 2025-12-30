-- 1. PROFILES (Public data for authenticated users)
create table profiles (
  id uuid references auth.users on delete cascade primary key,
  email text,
  full_name text,
  avatar_url text,
  subscription_tier text default 'free', -- free, solo, team
  created_at timestamp with time zone default timezone('utc'::text, now()) not null
);

-- 2. TEAMS (Workspace for Solo or Group)
create table teams (
  id uuid default gen_random_uuid() primary key,
  name text not null,
  owner_id uuid references profiles(id) not null,
  created_at timestamp with time zone default timezone('utc'::text, now()) not null
);

-- 3. TEAM MEMBERS (Who belongs to which team)
create table team_members (
  id uuid default gen_random_uuid() primary key,
  team_id uuid references teams(id) on delete cascade not null,
  user_id uuid references profiles(id) on delete cascade not null,
  role text default 'member', -- owner, member, viewer
  unique(team_id, user_id)
);

-- 4. PROJECTS (The main container)
create table projects (
  id uuid default gen_random_uuid() primary key,
  team_id uuid references teams(id) on delete cascade not null,
  client_name text,
  client_email text,
  title text not null default 'New Project',
  status text default 'new', -- new, intake_in_progress, brief_ready, proposal_sent, closed
  created_at timestamp with time zone default timezone('utc'::text, now()) not null
);

-- 5. INTAKES (The AI Interview Session)
create table intakes (
  id uuid default gen_random_uuid() primary key,
  project_id uuid references projects(id) on delete cascade not null,
  method text default 'link', -- link (client uses AI), proxy (freelancer pastes text)
  share_link_token text unique, -- The magic link code
  transcript jsonb, -- Stores the full chat history
  client_answers jsonb, -- Stores the final raw answers
  status text default 'active', -- active, completed
  created_at timestamp with time zone default timezone('utc'::text, now()) not null
);

-- 6. BRIEFS (The Technical Scope - AI Generated)
create table briefs (
  id uuid default gen_random_uuid() primary key,
  project_id uuid references projects(id) on delete cascade not null,
  content jsonb not null, -- { summary: "...", features: [...] }
  version int default 1,
  is_locked boolean default false,
  created_at timestamp with time zone default timezone('utc'::text, now()) not null
);

-- 7. PROPOSALS (The Financial Quote)
create table proposals (
  id uuid default gen_random_uuid() primary key,
  project_id uuid references projects(id) on delete cascade not null,
  brief_id uuid references briefs(id) not null, -- Links to specific scope version
  currency text default 'USD',
  total_amount numeric,
  deposit_amount numeric,
  timeline_estimate text,
  content jsonb, -- { sections: [ {title: "Design", price: 500} ] }
  status text default 'draft', -- draft, sent, accepted, rejected
  created_at timestamp with time zone default timezone('utc'::text, now()) not null
);

-- SECURITY POLICIES (Row Level Security - RLS)
-- Crucial: Enable RLS so users can only see their own teams' data
alter table profiles enable row level security;
alter table teams enable row level security;
alter table projects enable row level security;